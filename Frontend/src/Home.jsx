import React, { useEffect, useState } from 'react'
import BookmarkCard from './components/BookmarkCard'
import './Home.css'

const API_BASE = 'http://localhost:8080/api/bookmarks'

const Home = () => {
  const [bookmarks, setBookmarks] = useState([])
  const [showForm, setShowForm] = useState(false)
  const [form, setForm] = useState({ title: '', url: '', image: '', description: '' })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchBookmarks()
  }, [])

  const fetchBookmarks = async () => {
    setLoading(true)
    setError(null)
    try {
      const res = await fetch(API_BASE)
      if (!res.ok) throw new Error('Network response was not ok')
      const data = await res.json()
      setBookmarks(Array.isArray(data) ? data : [])
    } catch (err) {
      console.warn('Failed to fetch bookmarks, falling back to empty list', err)
      setError('Could not load bookmarks')
      setBookmarks([])
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleAdd = async (e) => {
    e.preventDefault()
    if (!form.url) return
    const payload = { ...form }

    try {
      const res = await fetch(API_BASE, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
      if (!res.ok) throw new Error('Failed to add bookmark')
      // Try to read created resource from server
      const created = await res.json()
      if (created) {
        setBookmarks(prev => [created, ...prev])
      } else {
        // server didn't return created object; optimistically add
        setBookmarks(prev => [{ id: Date.now(), ...payload }, ...prev])
      }
      setForm({ title: '', url: '', image: '', description: '' })
      setShowForm(false)
    } catch (err) {
      console.error(err)
      // fallback to local optimistic add
      setBookmarks(prev => [{ id: Date.now(), ...payload }, ...prev])
      setForm({ title: '', url: '', image: '', description: '' })
      setShowForm(false)
    }
  }

  const handleDelete = async (id) => {
    // optimistic UI
    const prev = bookmarks
    setBookmarks(prev => prev.filter(b => b.id !== id))
    try {
      const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' })
      if (!res.ok) throw new Error('Delete failed')
    } catch (err) {
      console.error('Failed to delete on server, restoring item', err)
      setBookmarks(prev)
    }
  }

  return (
    <div className="home-root">
      <header className="home-header">
        <h1>Bookmarks</h1>
        <div>
          <button className="add-btn" onClick={() => setShowForm(s => !s)}>+ Add URL</button>
        </div>
      </header>

      {showForm && (
        <div className="add-form">
          <form onSubmit={handleAdd}>
            <input name="title" value={form.title} onChange={handleChange} placeholder="Title (optional)" />
            <input name="url" value={form.url} onChange={handleChange} placeholder="https://example.com" required />
            <input name="image" value={form.image} onChange={handleChange} placeholder="Image URL (optional)" />
            <textarea name="description" value={form.description} onChange={handleChange} placeholder="Description (optional)" />
            <div className="form-actions">
              <button type="submit" className="submit">Add</button>
              <button type="button" className="cancel" onClick={() => setShowForm(false)}>Cancel</button>
            </div>
          </form>
        </div>
      )}

      {loading && <p className="status">Loading...</p>}
      {error && <p className="status error">{error}</p>}

      <div className="grid">
        {bookmarks.length === 0 && !loading ? (
          <p className="empty">No bookmarks yet — add one!</p>
        ) : (
          bookmarks.map(b => (
            <BookmarkCard key={b.id || b.url} bookmark={b} onDelete={handleDelete} />
          ))
        )}
      </div>
    </div>
  )
}

export default Home
