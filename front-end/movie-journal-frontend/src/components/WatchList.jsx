import React, { useEffect, useState } from "react";
import {
  getWatchlistByUserId,
  deleteWatchlist,
  addToWatchlist,
  updateWatchlist
} from "../service/watchListService";
import { getMovieById, getMovieByTitle } from "../service/ListMovies";
import "./css/WatchList.css";

const WatchList = () => {
  const [watchlist, setWatchlist] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [movieTitle, setMovieTitle] = useState("");
  const [status, setStatus] = useState("");
  const [note, setNote] = useState("");
  const [editingId, setEditingId] = useState(null);

  const userId = 1;

  useEffect(() => {
    fetchWatchlist();
  }, []);

  const fetchWatchlist = async () => {
    try {
      const response = await getWatchlistByUserId(userId);
      const watchlistItems = response?.data ?? [];

      const items = await Promise.all(
        watchlistItems.map(async (item) => {
          try {
            const movieRes = await getMovieById(item.movieId);
            return {
              ...item,
              movie: movieRes?.data ?? {
                title: "Unknown",
                posterUrl: "",
                genre: "N/A",
                releaseYear: 0
              }
            };
          } catch (movieErr) {
            return {
              ...item,
              movie: {
                title: "Unknown",
                posterUrl: "",
                genre: "N/A",
                releaseYear: 0
              }
            };
          }
        })
      );

      setWatchlist(items);
    } catch (err) {
      console.error("Failed to load watchlist", err);
    }
  };

  const handleDelete = async (watchId) => {
    try {
      await deleteWatchlist(watchId);
      setWatchlist((prev) => prev.filter((item) => item.watchId !== watchId));
    } catch (err) {
      console.error("Failed to delete watchlist item:", err);
    }
  };

  const handleAdd = async (e) => {
    e.preventDefault();

    try {
      const movieRes = await getMovieByTitle(movieTitle);
      const movie = movieRes?.data;

      if (!movie || !movie.movieId) {
        alert("Movie not found");
        return;
      }

      const newEntry = {
        movieId: movie.movieId,
        userId: userId,
        status: status,
        note: note,
      };

      await addToWatchlist(newEntry);
      fetchWatchlist();
      resetForm();
    } catch (err) {
      console.error("Error adding movie to watchlist", err);
    }
  };

  const handleEdit = (item) => {
    setEditingId(item.watchId);
    setMovieTitle(item.movie.title);
    setStatus(item.status);
    setNote(item.note);
    setShowForm(true);
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const updatedEntry = {
        status: status,
        note: note,
      };

      await updateWatchlist(editingId, updatedEntry);
      fetchWatchlist();
      resetForm();
    } catch (err) {
      console.error("Error updating watchlist item", err);
    }
  };

  const resetForm = () => {
    setShowForm(false);
    setMovieTitle("");
    setStatus("");
    setNote("");
    setEditingId(null);
  };

  return (
    <div className="watchlist-container">
      <h2 className="watchlist-title">Your WatchLists</h2>

      <button className="add-btn" onClick={() => { resetForm(); setShowForm(!showForm); }}>
        {showForm ? "Close Form" : "Add to Watchlist"}
      </button>

      {showForm && (
        <form className="watchlist-form" onSubmit={editingId ? handleUpdate : handleAdd}>
          <input
            type="text"
            placeholder="Enter movie title"
            value={movieTitle}
            onChange={(e) => setMovieTitle(e.target.value)}
            disabled={editingId !== null} // Prevent editing title in update mode
            required
          />
          <select value={status} onChange={(e) => setStatus(e.target.value)} required>
            <option value="">Select status</option>
            <option value="PLANNED">Planned</option>
            <option value="WATCHING">Watching</option>
            <option value="COMPLETED">Completed</option>
          </select>
          <input
            type="text"
            placeholder="Note (optional)"
            value={note}
            onChange={(e) => setNote(e.target.value)}
          />
          <button type="submit">{editingId ? "Update Watchlist" : "Add to Watchlist"}</button>
        </form>
      )}

      {watchlist.map((item) => (
        <div className="watchlist-item" key={item.watchId}>
          <img
            src={item.movie.posterUrl || "src/assets/poster.jpg"}
            alt={item.movie.title}
          />
          <div className="watchlist-details">
            <h3>{item.movie.title}</h3>
            <p>Status: {item.status}</p>
            <p>Note: {item.note}</p>
          </div>
          <div className="watchlist-actions">
            <button onClick={() => handleEdit(item)}>Edit</button>
            <br />
            <button onClick={() => handleDelete(item.watchId)}>Delete</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default WatchList;
