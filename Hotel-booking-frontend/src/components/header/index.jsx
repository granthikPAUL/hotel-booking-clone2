import React from "react";
import "./index.scss";
import { FaPhoneAlt } from "react-icons/fa";
import { TbWorld } from "react-icons/tb";
import { Link } from "react-router-dom";
import Logo from "../../assets/logo/logo.png";
const index = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow px-1 px-lg-5">
      <section className="navbar-flex navbar-brand logo d-flex justify-content-center align-items-center">
        <Link
          to="/"
          className="d-flex align-items-center justify-content-center"
        >
          <h4 className="m-0 logo-text">SnapStay</h4>
          <img src={Logo} alt="Hotel.com logo" />
        </Link>
      </section>
      <button
        className="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav mr-auto d-flex gap-4 align-items-center ms-auto">
          <li className="nav-item contact me-auto">
            <div className="d-flex align-items-center gap-2">
              <FaPhoneAlt /> 0124-6201611
            </div>
          </li>
          <li className="nav-item language me-auto">
            <div className="d-flex align-items-center gap-2">
              <TbWorld /> English
            </div>
          </li>
          <li className="nav-item support me-auto">
            <div className="d-flex align-items-center gap-2">Support</div>
          </li>
          <li>
            <Link to="/register">Regiser</Link>
          </li>
          <li>
            <Link to="/login">Login</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default index;
