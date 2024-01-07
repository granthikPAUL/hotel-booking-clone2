import React from "react";
import "./index.scss";

const HotelCard = ({ img, data }) => {
  const {
    hotel_name,
    amenities_provided,
    hotel_address,
    hotel_city,
    hotel_id,
    total_rooms,
    rating,
    hotelRooms,
  } = data;
  const price_per_night = Math.floor(
    Math.random() * (30000 - 10000 + 1) + 10000
  );
  const discount = Math.floor(Math.random() * (40 - 10 + 1) + 10);
  return (
    <div className="card HotelCard">
      <img src={img} className="card-img-top" alt={img} />
      <div className="card-body">
        <p className="rating">
          {rating}/10{" "}
          {rating < 9
            ? "Very good"
            : rating > 9 && rating < 9.5
            ? "Wonderful"
            : "Exceptional"}{" "}
          ({Math.floor(Math.random() * (1000 - 400 + 1) + 400)}&nbsp;reviews)
        </p>
        <h6 className="card-title">{hotel_name}</h6>
        <p className="card-text">
          {hotel_city.replace(/\b\w/g, (char) => char.toUpperCase())}
        </p>
        <p className="price">
          ₹
          {Math.floor(price_per_night - price_per_night * (discount / 100))
            .toString()
            .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
          &nbsp;
          <s>
            ₹{price_per_night.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
          </s>
          <p>per night</p>
          <p>includes taxes & fees</p>
        </p>
        <p className="discount">{discount}% off</p>
      </div>
    </div>
  );
};

export default HotelCard;
