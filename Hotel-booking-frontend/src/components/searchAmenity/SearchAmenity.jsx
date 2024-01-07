import cottage from "../../assets/images/cottage.svg";
import apartHotel from "../../assets/images/apartHotel.svg";
import allInclusive from "../../assets/images/allInclusive.svg";
import apartment from "../../assets/images/apartment.svg";
import familyFriendly from "../../assets/images/familyFriendly.svg";
import hotTub from "../../assets/images/hotTub.svg";
import houseboat from "../../assets/images/houseboat.svg";
import pool from "../../assets/images/pool.svg";
import resort from "../../assets/images/resort.svg";
import seaview from "../../assets/images/seaview.svg";
import spa from "../../assets/images/spa.svg";
import villa from "../../assets/images/villa.svg";

import "./index.scss";

const SearchAmenity = () => {
  const favStaty = [
    {
      img: cottage,
      text: "Cottage",
    },
    {
      img: apartHotel,
      text: "Apart hotel",
    },
    {
      img: allInclusive,
      text: "All inclusive",
    },
    {
      img: apartment,
      text: "Apartment",
    },
    {
      img: familyFriendly,
      text: "Family friendly",
    },
    {
      img: hotTub,
      text: "Hot tub",
    },
    {
      img: houseboat,
      text: "Houseboat",
    },
    {
      img: pool,
      text: "Pool",
    },
    {
      img: resort,
      text: "Resort",
    },
    {
      img: seaview,
      text: "Sea view",
    },
    {
      img: spa,
      text: "Spa",
    },
    {
      img: villa,
      text: "Villa",
    },
  ];
  return (
    <div className="search-by-amenity">
      <h3>Discover your new favourite stay</h3>
      <ul className="mt-3">
        {favStaty.map((item, index) => (
          <li key={index}>
            <div className="img-wrapper">
              <img src={item.img} alt={item.text} />
            </div>
            <p>{item.text}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchAmenity;
