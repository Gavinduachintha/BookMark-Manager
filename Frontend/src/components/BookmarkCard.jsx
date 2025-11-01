import React from 'react'
import './BookmarkCard.css'

const BookmarkCard = ({ bookmark, onDelete }) => {
  const { id, title, url, image, description } = bookmark || {}

  const getHost = (urlStr) => {
    try {
      return new URL(urlStr).hostname
    } catch {
      return ''
    }
  }

  const host = getHost(url)
  const imgSrc = image || (host ? `https://www.google.com/s2/favicons?domain=${host}&sz=128` : 'https://via.placeholder.com/200?text=No+Image')

  return (
    <div className="bm-card">
      <button className="bm-delete" onClick={() => onDelete && onDelete(id)} title="Delete bookmark">×</button>
      <div className="bm-image">
        <img
          src={imgSrc}
          alt={title || host}
          onError={(e) => (e.currentTarget.src = 'https://via.placeholder.com/200?text=No+Image')}
        />
      </div>
      <div className="bm-body">
        <h3 className="bm-title">{title || host || 'Untitled'}</h3>
        {host && <p className="bm-host">{host}</p>}
        {description && <p className="bm-desc">{description}</p>}
        {url && (
          <a className="bm-visit" href={url} target="_blank" rel="noreferrer">Visit</a>
        )}
      </div>
    </div>
  )
}

export default BookmarkCard

