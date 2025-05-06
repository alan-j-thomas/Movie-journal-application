import { useState } from 'react'
import './App.css'
import MovieCard from './components/MovieCard'
import WatchList from './components/WatchList'
import Journals from './components/Journals'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router";
import NavBar from './components/NavBar';
import Home from './components/Home';
import Login from './components/Login'





function App() {
  const [count, setCount] = useState(0)
  const [user, setUser] = useState(null);

  const handleLogin = (user) => {
    setUser(user); // Store the user token
  };


  const routes = createBrowserRouter([

    {
      path: "/login",
      element: <div>

        <Login onLogin={handleLogin} />
      </div>
    },

    {
      path: "/",
      element: <div>
        <NavBar />
        <Home />
      </div>
    },

    {
      path: "/movies",
      element: <div>
        <NavBar />
        <MovieCard />
      </div>
    },

    {
      path: "/watchlists",
      element: <div>
        <NavBar />
        <WatchList />
      </div>
    },

    {
      path: "/journals",
      element: <div>
        <NavBar />
        <Journals />
      </div>
    }
  ])

  return (
    <>

      <RouterProvider router={routes}>

      </RouterProvider>
    </>
  )
}

export default App
