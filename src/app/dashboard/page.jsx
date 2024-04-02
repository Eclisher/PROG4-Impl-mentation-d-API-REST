import React from "react";
import styles from "@/app/ui/dashboard/dashboard.module.css";
import Chart from "../ui/dashboard/chart/chart";

const Dashboard = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.main}>
        <Chart />
      </div>
    </div>
  );
};

export default Dashboard;
