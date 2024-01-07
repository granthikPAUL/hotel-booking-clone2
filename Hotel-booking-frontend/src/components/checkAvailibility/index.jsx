import React, { useEffect, useRef, useState } from "react";
import "./index.scss";
import Calendar from "react-calendar";
import { CgCalendarDates } from "react-icons/cg";
import { IoLocationSharp } from "react-icons/io5";
import { Link } from "react-router-dom";
import "react-calendar/dist/Calendar.css";

const CheckAvailibility = () => {
  const [location, setLocation] = useState("");
  const [date, setDate] = useState("");
  const [roomNo, setRoomNo] = useState(1);
  const [adultNo, setAdultNo] = useState(1);
  const [visibleCalendar, setVisibleCalendar] = useState(false);

  const calendarRef = useRef(null);

  const onSubmitCheck = (e) => {
    e.preventDefault();

    const formData = {
      location,
      checkIn: date[0],
      checkOut: date[1],
      roomNo,
      adultNo,
    };

    console.log("formData-->", formData);
    alert(JSON.stringify(formData));
  };

  const toggleCalendar = () => {
    setVisibleCalendar((v) => !v);
  };

  useEffect(() => {
    window.addEventListener("click", handleWindowClick);

    return () => window.removeEventListener("click", handleWindowClick);
  }, []);

  const handleWindowClick = (e) => {
    if (calendarRef.current && !calendarRef.current.contains(e.target)) {
      setVisibleCalendar(false);
    }
  };

  return (
    <div className="checkAvailibility py-4">
      <section className="CA-heading mx-auto">
        <h1 className="p-0 m-0">Where to?</h1>
      </section>

      <form onSubmit={onSubmitCheck} className="CA-form mx-auto">
        <div className="row CAFormElements justify-content-center p-2">
          {/* Where to? */}
          <div className="col-12 col-md-6 col-lg-3 d-flex align-items-center px-2">
            <div className="input-group px-0">
              <span className="input-group-text" id="basic-addon1">
                <IoLocationSharp size={20} />
              </span>
              <input
                type="text"
                className="form-control"
                placeholder="Where are you going?"
                value={location}
                onChange={(e) => setLocation(e.target.value)}
              />
            </div>
          </div>
          {/* Where to? */}

          {/* checkin & checkout  */}
          <div
            className="checkinCheckOutWrapper col-12 col-md-6 col-lg-4 row mx-0 px-2 py-4 py-md-0"
            ref={calendarRef}
          >
            <div className="input-group px-0" onClick={toggleCalendar}>
              <span className="input-group-text" id="basic-addon1">
                <CgCalendarDates size={20} />
              </span>
              <input
                className="form-control"
                type="text"
                readOnly
                value={
                  date &&
                  date?.length > 0 &&
                  date[0]?.toDateString() + " - " + date[1]?.toDateString()
                }
                placeholder="Check-in Date - Check-out Date"
              />
            </div>
            {visibleCalendar && (
              <div className="calendar-container ">
                <Calendar
                  onChange={setDate}
                  value={date || new Date()}
                  selectRange={true}
                  minDate={new Date()}
                />
              </div>
            )}
          </div>
          {/* checkin & checkout  */}

          {/* Room, adult  */}
          <div className="col-12 col-lg-4 row checkAvailibilityDetails mx-0 px-0 py-0 py-md-4 py-lg-0">
            <div
              title="No. of Rooms"
              className="custom-room-marketing col-6 d-flex align-items-center px-2"
            >
              <select
                name="Rooms"
                id="Rooms"
                value={roomNo}
                onChange={(e) => setRoomNo(e.target.value)}
                className="form-select"
              >
                <option value="1">1 Room</option>
                <option value="2">2 Rooms</option>
                <option value="3">3 Rooms</option>
                <option value="4">4 Rooms</option>
                <option value="5">5 Rooms</option>
                <option value="6">6+ Rooms</option>
              </select>
            </div>

            <div
              title="No. of Adults"
              className="custom-adults-marketing col-6 d-flex align-items-center px-2"
            >
              <select
                name="Adults"
                id="adults"
                value={adultNo}
                onChange={(e) => setAdultNo(e.target.value)}
                className="form-select"
              >
                <option value="1">1 Adult</option>
                <option value="2">2 Adults</option>
                <option value="3">3 Adults</option>
                <option value="4">4 Adults</option>
                <option value="5">5 Adults</option>
              </select>
            </div>
          </div>
          {/* Room, adult, child  */}

          {/* Search Btn */}
          <div className="col-12 col-lg-1 searchBtn d-flex align-items-center justify-content-center px-2 pt-4 pt-md-0">
            <button
              className="book-a-room btn btn-primary"
              type="submit"
              id="book-a-room"
              title="Search"
            >
              SEARCH
            </button>
          </div>
          {/* Search Btn */}
        </div>
      </form>
    </div>
  );
};

export default CheckAvailibility;
