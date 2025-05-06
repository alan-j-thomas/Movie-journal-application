import React from "react";
import { NavLink } from "react-router-dom";
import "./css/Home.css"; 

const Home = () => {
  return (
    <div className="home-container">
      <h1 className="home-title">Welcome to Movie Journal App </h1>
      <p className="home-description">
        Track your favorite movies, manage your watchlist, and never miss a film again!
      </p>

      <div className="home-buttons">
        <NavLink to="/watchlists" className="home-btn">
          View Your Watchlist
        </NavLink>
        <NavLink to="/journals" className="home-btn">
          View Your Journals
        </NavLink>
      </div>
    </div>
  );
};

export default Home;
