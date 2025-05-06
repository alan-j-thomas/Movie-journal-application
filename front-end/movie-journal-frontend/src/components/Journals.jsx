import React, { useEffect, useState } from 'react';
import JournalService from '../service/journalService';
import '../components/css/Journals.css';
import { getMovieByTitle } from '../service/ListMovies';

function Journals() {
  const [journals, setJournals] = useState([]);
  const [journalForm, setJournalForm] = useState({ title: '', content: '', moodTag: '' });
  const [movieName, setMovieName] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [openMenuId, setOpenMenuId] = useState(null);

  useEffect(() => {
    fetchJournals();
  }, []);

  const fetchJournals = async () => {
    try {
      const { data } = await JournalService.getAllJournals();
      setJournals([...data]);
    } catch (error) {
      console.error("Error fetching journals:", error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setJournalForm((prev) => ({ ...prev, [name]: value }));
  };

  const resetForm = () => {
    setJournalForm({ title: '', content: '', moodTag: '' });
    setMovieName('');
    setShowForm(false);
    setEditingId(null);
  };

  const handleAddJournal = async (e) => {
    e.preventDefault();
    if (!journalForm.title.trim() || !journalForm.content.trim()) return;

    try {
      let movieId = null;

      if (movieName.trim()) {
        const movieRes = await getMovieByTitle(movieName);
        const movie = movieRes?.data;
        if (!movie || !movie.movieId) {
          alert("Movie not found");
          return;
        }
        movieId = movie.movieId;
      }

      const journalData = {
        ...journalForm,
        userId: 1,
        movieTitle: movieName,

      };

      await JournalService.addToJournal(journalData);
      fetchJournals();
      resetForm();
    } catch (error) {
      console.error('Error adding journal:', error);
    }
  };

  const handleEdit = (journal) => {
    setEditingId(journal.journalId);
    setJournalForm({
      title: journal.title,
      content: journal.content,
      moodTag: journal.moodTag || '',
    });
    setMovieName(journal.movies?.[0]?.title || '');
    setShowForm(true);
    setOpenMenuId(null);
  };

  const handleUpdateJournal = async (e) => {
    e.preventDefault();
    if (!journalForm.title.trim() || !journalForm.content.trim()) return;

    try {
      let movieId = null;

      if (movieName.trim()) {
        const movieRes = await getMovieByTitle(movieName);
        const movie = movieRes?.data;
        if (!movie || !movie.movieId) {
          alert("Movie not found");
          return;
        }
        movieId = movie.movieId;
      }

      const updatedJournal = {
        journalId: editingId,
        ...journalForm,
        movieTitle: movieName,
        movieId: movieId
      };

      const response = await JournalService.updateJournal(editingId, updatedJournal);
      fetchJournals();
      resetForm();
    } catch (error) {
      console.error("Error updating journal:", error);
    }
  };


  const handleDeleteJournal = async (journalId) => {
    try {
      await JournalService.deleteJournal(journalId);
      setOpenMenuId(null);
      fetchJournals();
    } catch (error) {
      console.error('Error deleting journal:', error);
    }
  };

  const toggleMenu = (journalId) => {
    setOpenMenuId((prev) => (prev === journalId ? null : journalId));
  };

  return (
    <div className="journals-page">
      <h2 className="page-title">Journals</h2>

      <button className='toggle-form-btn'
        onClick={() => {
          if (showForm) {
            resetForm();
          } else {
            setShowForm(true);
          }
        }}>
        {showForm ? "Close Journal Form" : "Add New Journal"}
      </button>


      {showForm && (
        <form className="journal-form" onSubmit={editingId ? handleUpdateJournal : handleAddJournal}>
          <input
            type="text"
            placeholder="Movie name"
            value={movieName}
            onChange={(e) => setMovieName(e.target.value)}
          />
          <input
            type="text"
            name="title"
            placeholder="Journal Title"
            value={journalForm.title}
            onChange={handleInputChange}
            maxLength={100}
            required
          />
          <textarea
            name="content"
            placeholder="Write your thoughts here..."
            value={journalForm.content}
            onChange={handleInputChange}
            rows="5"
            required
          />
          <input
            type="text"
            name="moodTag"
            placeholder="Mood (e.g., Happy, Nostalgic)"
            value={journalForm.moodTag}
            onChange={handleInputChange}
            maxLength={30}
          />
          <button type="submit">{editingId ? "Update Journal" : "Add Journal"}</button>
        </form>
      )}

      <div className="journals-grid">
        {journals.length === 0 ? (
          <div className="no-journals">
            No journals yet. Start documenting your journey!
          </div>
        ) : (
          journals.map((journal) => (
            <div key={journal.journalId} className="journal-card">
              <div className="card-top">
                {journal.movies?.[0]?.posterUrl ? (
                  <img
                    src={journal.movies[0].posterUrl}
                    alt={journal.movies[0].title}
                    className="card-poster"
                  />
                ) : (
                  <div className="no-poster">No Poster</div>
                )}

                <div className="menu-container">
                  <button className="journal-menu-button" onClick={() => toggleMenu(journal.journalId)}> ⋮ </button>
                  {openMenuId === journal.journalId && (
                    <div className="journal-dropdown-menu">
                      <button onClick={() => handleEdit(journal)}>Edit</button>
                      <button onClick={() => handleDeleteJournal(journal.journalId)}>Delete</button>
                    </div>
                  )}
                </div>
              </div>

              <div className="card-body">
                <h3 className="card-title">{journal.title}</h3>
                <p className="card-content">{journal.content}</p>
                {journal.moodTag && (
                  <p className="card-mood"><strong>Mood:</strong> {journal.moodTag}</p>
                )}
                {journal.movies?.length > 0 && (
                  <div className="movies-mentioned">
                    <strong>Movies Mentioned:</strong>
                    <ul>
                      {journal.movies.map((movie) => (
                        <li key={movie.movieId}>
                          {movie.title} ({movie.releaseYear})
                        </li>
                      ))}
                    </ul>
                  </div>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default Journals;
