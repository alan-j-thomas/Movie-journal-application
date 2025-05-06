import React, { useState } from 'react'
import '../components/css/MovieCard.css'
import { listMovies } from '../service/ListMovies';
import { useEffect } from 'react';

const MovieCard = () => {

    const [movies, setMovies] = useState([]);

    useEffect(() => {
      listMovies().then((response) => {
        setMovies(response.data);
      }).catch(err => {
        console.error(err);
      });
      
    }, [])
    
  return (
    <div className='card-container'>
      {movies.map((movie) => (
        <div className='container' key={movie.id}>
            <img id='movie-poster' src={movie.posterUrl || 'src/assets/poster.jpg'} />
            <h2>{movie.title}</h2>
            <p>{movie.genre}</p>
          
        </div>
      ))}
    </div>
  )
}

export default MovieCard