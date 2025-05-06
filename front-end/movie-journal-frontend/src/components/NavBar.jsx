import React from 'react'
import {NavLink} from "react-router-dom"
import '../components/css/NavBar.css'

const NavBar = () => {
  return (
    <div className='container-nav'>

        <div className='inner-container'>
          <img src='src/assets/logo.png' id='logo'/>
          <h2>Movie Journal App</h2>
        </div>
        
        <div className='nav-links'>
            <NavLink to="/">Home</NavLink>
            <NavLink to="/movies">Movies</NavLink>
            <NavLink to="/watchlists">WatchLists</NavLink>
            <NavLink to="/journals">Journals</NavLink>
        </div>
        
    </div>
  )
}

export default NavBar