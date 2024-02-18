import React, { useEffect, useRef, useState } from "react";
import "./index.scss";
import { FaPhoneAlt } from "react-icons/fa";
import { TbWorld } from "react-icons/tb";
import { CgProfile } from "react-icons/cg";
import { BiSupport } from "react-icons/bi";
import { IoPersonCircle } from "react-icons/io5";
import { Link, useNavigate } from "react-router-dom";
import Logo from "../../assets/logo/logo.png";
import { useDispatch, useSelector } from "react-redux";
import { loginState } from "../../redux/reducers";

const index = () => {
  const [profileExpand, setprofileExpand] = useState(false);
  const loginRef = useRef(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const globalState = useSelector((state) => state.globalState);

  useEffect(() => {
    const handleClick = (e) => {
      if (loginRef.current && !loginRef.current.contains(e.target)) {
        setprofileExpand(false);
      }
    };
    document.addEventListener("click", handleClick);
    return () => {
      document.removeEventListener("click", handleClick);
    };
  }, [loginRef]);

  const handleLogout = () => {
    dispatch(loginState(false));
    navigate("/");
  };

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
            <div className="d-flex align-items-center gap-2">
              <BiSupport />
              Support
            </div>
          </li>
          {globalState.login ? (
            <li className="login" ref={loginRef}>
              <Link
                className="d-flex align-items-center gap-2"
                onClick={() => setprofileExpand(true)}
              >
                <IoPersonCircle size={20} />
                Welcome, Priyam
              </Link>
              {profileExpand && (
                <section className="profile-dropdown">
                  <ul className="list-group">
                    <li
                      className="list-group-item list-group-item-action"
                      onClick={() => navigate("/profile")}
                    >
                      My Profile
                    </li>
                    <li className="list-group-item list-group-item-action">
                      Help
                    </li>
                    <li className="list-group-item list-group-item-action">
                      About Us
                    </li>
                    <li
                      className="list-group-item list-group-item-action"
                      onClick={handleLogout}
                    >
                      Logout
                    </li>
                  </ul>
                </section>
              )}
            </li>
          ) : (
            <li>
              <Link to="/login" className="d-flex align-items-center gap-2">
                <CgProfile size={17} />
                Login / Signup
              </Link>
            </li>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default index;
