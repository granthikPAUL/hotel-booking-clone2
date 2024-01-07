import React from "react";
import "./index.scss";
import { data } from "../../assets/Hotel-static-data/data";
import Banner from "../../components/homeBanner/Banner";
import SearchAmenity from "../../components/searchAmenity/SearchAmenity";
import SearchCity from "../../components/searchCity/SearchCity";
import HotelListing from "../../components/hotelListing/HotelListing";

const index = () => {
  return (
    <div className="home">
      <section>
        <Banner />
      </section>
      <section className="my-3">
        <SearchAmenity />
      </section>
      <section>
        <SearchCity />
      </section>
      <section className="my-3">
        <HotelListing imgData={data} />
      </section>
    </div>
  );
};

export default index;
