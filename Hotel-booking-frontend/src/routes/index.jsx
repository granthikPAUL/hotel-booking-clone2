import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  createBrowserRouter,
  Outlet,
} from "react-router-dom";

import Header from "../components/header";
import CheckAvailibility from "../components/checkAvailibility";
import Footer from "../components/footer";
import Homepage from "../pages/homepage";
import Register from "../pages/register";
import Login from "../pages/login";
import Checkout from "../pages/checkout";
import Error from "../components/Error/Error";

const HotelRoutes = () => {
  return (
    <>
      <Header />
      <CheckAvailibility />
      <Outlet />
      <Footer />
    </>
  );
};

export const hotelRouter = createBrowserRouter([
  {
    path: "/",
    element: <HotelRoutes />,
    children: [
      {
        path: "/",
        element: <Homepage />,
      },
      {
        path: "/register",
        element: <Register />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/checkout",
        element: <Checkout />,
      },
    ],
    errorElement: <Error />,
  },
]);
