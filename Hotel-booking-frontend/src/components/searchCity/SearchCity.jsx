import "./index.scss";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { trendingDestination } from "../../assets/Hotel-static-data/data";
import { FcPrevious, FcNext } from "react-icons/fc";
const SearchCity = () => {
  const settings = {
    // dots: true,
    arrows: true,
    // fade: true,
    infinite: true,
    // autoplay: true,
    speed: 500,
    autoplaySpeed: 5000,
    slidesToShow: 4,
    slidesToScroll: 1,
    prevArrow: <FcPrevious />,
    nextArrow: <FcNext />,
    responsive: [
      {
        breakpoint: 992,
        settings: {
          slidesToShow: 3,
        },
      },
      {
        breakpoint: 767,
        settings: {
          slidesToShow: 2,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
        },
      },
    ],
  };
  return (
    <div className="search-by-city">
      <h3>Explore stays in trending destinations</h3>
      <Slider {...settings}>
        {trendingDestination.map((item, index) => (
          <div className="card">
            <img src={item.image} className="card-img-top" alt={item.city} />
            <div className="card-body">
              <h5 className="card-title">{item.city}</h5>
            </div>
          </div>
        ))}
      </Slider>
    </div>
  );
};

export default SearchCity;
