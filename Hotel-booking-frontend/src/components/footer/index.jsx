import React from "react";
import "./index.scss";
import { footerData } from "../../assets/Hotel-static-data/data";
import { Link } from "react-router-dom";

const index = () => {
  return (
    <div className="footer my-5 container">
      <div className="footer-category-wrapper">
        {footerData.map((each, index) => (
          <section className="footer-category" key={index}>
            <p>{each.category}</p>
            <div className="sub-items">
              {each.items.map((item, subIndex) => (
                <Link key={subIndex} to={item.link}>
                  {item.title}
                </Link>
              ))}
            </div>
          </section>
        ))}
      </div>
      <hr />
      <div className="footer-copyright">
        <h6 className="text-center">Made with 💜 and React</h6>
      </div>
    </div>
  );
};

export default index;
