import React from "react";
import "./index.scss";
import { footerData } from "../../assets/Hotel-static-data/data";
import { Link } from "react-router-dom";

const index = () => {
  return (
    <div className="footer my-5">
      <h3>expedia group</h3>
      <div className="footer-category-wrapper">
        {footerData.map((each, index) => (
          <section className="footer-category" key={index}>
            <p>{each.category}</p>
            <div className="sub-items">
              {each.items.map((item, index) => (
                <Link to={item.link}>{item.title}</Link>
              ))}
            </div>
          </section>
        ))}
      </div>
      <hr />
      <div className="footer-copyright">
        <p>
          * Some hotels require you to cancel more than 24 hours before
          check-in. Details on site.{" "}
        </p>
        <p>
          © 2023 Hotels.com is an Expedia Group company. All rights reserved.
        </p>
        <p>
          Hotels.com and the Hotels.com logo are trademarks or registered
          trademarks of Hotels.com, LP in the United States and/or other
          countries. All other trademarks are the property of their respective
          owners.
        </p>
      </div>
    </div>
  );
};

export default index;
