import React, { useState } from "react"

const Card = ({title, image, description}) => {
    const [isBookmarked, setIsBookmarked] = useState(false)

    const handleBookmark = async () => {
        setIsBookmarked(!isBookmarked)

        try {
            const response = await fetch('/api/bookmarks', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title, image, description })
            })
            if (!response.ok) throw new Error('Bookmark failed')
        } catch (error) {
            console.error('Error bookmarking:', error)
            setIsBookmarked(!isBookmarked)
        }
    }

    return (
        <div className="card">
            <img src={image} alt={title} className="card-image"/>
            <div className="card-content">
                <h2 className="card-title">{title}</h2>
                <p className="card-description">{description}</p>
                <button
                    onClick={handleBookmark}
                    className={`bookmark-btn ${isBookmarked ? 'bookmarked' : ''}`}
                >
                    {isBookmarked ? '★' : '☆'} Bookmark
                </button>
            </div>
        </div>
    )
}

export default Card