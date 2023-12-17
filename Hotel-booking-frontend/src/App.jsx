import React from "react";
import { hotelRouter } from "./routes";
import { RouterProvider } from "react-router-dom";
const App = () => {
  return (
    <div className="app">
      <RouterProvider router={hotelRouter} />
    </div>
  );
};

export default App;
