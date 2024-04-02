"use client";
import React from "react";
import styles from "./navbar.module.css";
import { usePathname } from "next/navigation";
import { MdOutlineChat, MdOutlineNotifications, MdPublic, MdSearch } from "react-icons/md";

const Navbar = () => {
  const pathname = usePathname();
  return (
    <div className={styles.container}>
      <div className={styles.title}>{pathname.split("/").pop()}</div>
      <div className={styles.menu}>
        <div className={styles.search}>
          <MdSearch/>
          <input type="text" placeholder="Search..." id="searchBar" className={styles.input}/>
        </div>
        <div className={styles.icons}>
          <MdOutlineChat size={20}/>
          <MdOutlineNotifications size={20}/>
          <MdPublic size={20}/>
        </div>
      </div>
    </div>
  );
};
export default Navbar;
