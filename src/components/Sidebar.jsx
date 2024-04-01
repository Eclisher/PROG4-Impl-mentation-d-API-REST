// Sidebar.jsx
import React from "react";
import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div className="sidebar">
      <ul className="nav flex-column">
        <li className="nav-item">
          <Link to="/" className="nav-link">Home</Link>
        </li>
        <li className="nav-item">
          <Link to="/removal" className="nav-link">Removal</Link>
        </li>
        <li className="nav-item">
          <Link to="/balance" className="nav-link">Balance</Link>
        </li>
        <li className="nav-item">
          <Link to="/provisioning" className="nav-link">Provisioning</Link>
        </li>
        <li className="nav-item">
          <Link to="/transfer" className="nav-link">Transfer</Link>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
