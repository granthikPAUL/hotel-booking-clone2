import React from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  createBrowserRouter,
  Outlet,
} from "react-router-dom";

import Header from "../components/header";
import CheckAvailibility from "../components/checkAvailibility";
import Footer from "../components/footer";
import Homepage from "../pages/homepage";
import Signup from "../pages/signup";
import Login from "../pages/login";
import Profile from "../pages/profile";
import Checkout from "../pages/checkout";
import Error from "../components/Error/Error";

const HomepageRoute = () => (
  <>
    <Header />
    <CheckAvailibility />
    <Outlet />
    <Footer />
  </>
);

const ProfileRoute = () => (
  <>
    <Header />
    <Outlet />
    <Footer />
  </>
);

export const hotelRouter = createBrowserRouter([
  {
    path: "/",
    element: <HomepageRoute />,
    children: [
      {
        path: "",
        element: <Homepage />,
      },
    ],
    errorElement: <Error />,
  },
  {
    path: "/login",
    element: <Login />,
    errorElement: <Error />,
  },
  {
    path: "/signup",
    element: <Signup />,
    errorElement: <Error />,
  },
  {
    path: "/profile",
    element: <ProfileRoute />,
    children: [
      {
        path: "/profile",
        element: <Profile />,
      },
    ],
    errorElement: <Error />,
  },
  {
    path: "/checkout",
    element: <Checkout />,
    errorElement: <Error />,
  },
]);
