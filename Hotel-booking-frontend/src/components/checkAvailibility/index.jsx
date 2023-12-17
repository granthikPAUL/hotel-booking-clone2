import React, { useState } from "react";
import "./index.scss";
import Calendar from "react-calendar";
import { CgCalendarDates } from "react-icons/cg";
import { Link } from "react-router-dom";
import "react-calendar/dist/Calendar.css";

const CheckAvailibility = () => {
  const [roomNo, setRoomNo] = useState(1);
  const [adultNo, setAdultNo] = useState(0);
  const [visibleCalendar, setVisibleCalendar] = useState(false);
  const [date, setDate] = useState(null);

  const onSubmitCheck = () => {};

  const toggleCalendar = () => {
    setVisibleCalendar((v) => !v);
  };

  return (
    <div className="checkAvailibility">
      <section className="CA-heading mx-auto">
        <h1>Find your next stay</h1>
        <h3>Search deals on hotels, homes, and much more...</h3>
      </section>

      <form onSubmit={onSubmitCheck} className="CA-form mx-auto shadow">
        <div className="row px-2 py-4 CAFormElements justify-content-center">
          {/* Where to? */}
          <div className="col-3 d-flex align-items-center ps-0">
            <input
              type="text"
              className="form-control"
              placeholder="Where are you going?"
            />
          </div>
          {/* Where to? */}

          {/* checkin & checkout  */}
          <div className="checkinCheckOutWrapper col-4 row px-4">
            <input
              className="form-control"
              type="text"
              readOnly
              value={
                date && date?.length > 0 &&
                date[0]?.toDateString() + " - " + date[1]?.toDateString()
              }
              onClick={toggleCalendar}
              placeholder="Check-in Date - Check-out Date"
            />
            {/* <CgCalendarDates className="calendar-icon" size={30}/> */}
            {visibleCalendar && (
              <div className="calendar-container">
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
          <div className="checkAvailibilityDetails col-4 row">
            <div
              title="No. of Rooms"
              className="custom-room-marketing col-6 d-flex align-items-center"
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
              className="custom-adults-marketing col-6 d-flex align-items-center"
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
          <div className="searchBtn col-1 d-flex align-items-center">
            <Link>
              <button
                className="book-a-room btn btn-secondary"
                type="button"
                id="book-a-room"
                title="Search"
              >
                SEARCH
              </button>
            </Link>
          </div>
          {/* Search Btn */}
        </div>
      </form>
    </div>
  );
};

export default CheckAvailibility;
