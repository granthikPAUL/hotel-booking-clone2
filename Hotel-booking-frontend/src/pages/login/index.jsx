import React from "react";
import "./index.scss";
import loginImg from "../../assets/registration-login/login.svg";
import Logo from "../../assets/logo/logo.png";
import { Link } from "react-router-dom";
import { MdEmail } from "react-icons/md";
import { FaLock } from "react-icons/fa";

const index = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
  };
  return (
    <div className="login vh-100 vw-100 d-flex row p-0 m-0">
      <aside className="image-section col-12 col-lg-6">
        <img src={loginImg} alt="loginImg" />
      </aside>
      <section className="form-section col-12 col-lg-6">
        <Link to="/" className="logo">
          <h3 className="m-0 logo-txt">SnapStay</h3>
          <img className="logo-img" src={Logo} alt="SnapStay logo" />
        </Link>
        <form className="login-form px-3 px-md-5 py-4" onSubmit={handleSubmit}>
          <h2>Login</h2>
          <div class="input-group">
            <span class="input-group-text" id="basic-addon1">
              <MdEmail />
            </span>
            <input
              type="email"
              class="form-control"
              placeholder="Enter your email id"
            />
          </div>

          <div class="input-group">
            <span class="input-group-text" id="basic-addon1">
              <FaLock />
            </span>
            <input
              type="password"
              class="form-control"
              placeholder="Enter your password"
            />
          </div>

          <p>Forgot password?</p>
          <button className="btn login-btn">Login</button>
          <p className="text-center">
            Don't have an account? <Link to="/signup">Signup</Link>
          </p>
        </form>
      </section>
    </div>
  );
};

export default index;
