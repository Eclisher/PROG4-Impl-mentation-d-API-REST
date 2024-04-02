import React from "react";
import styles from "@/app/ui/dashboard/sidebar/sidebar.module.css";
import {
  MdAccountBalance,
  MdAccountBalanceWallet,
  MdDashboard,
  MdPeople,
  MdAttachMoney,
  MdPayment,
  MdSwapHoriz,
} from "react-icons/md";
import MenuLink from "./menuLink/menuLink";

const menuItems = [
  {
    title: "Bank Of Mada",
    list: [
      {
        title: "Dashboard",
        path: "/dashboard",
        icon: <MdDashboard />,
      },
      {
        title: "Accounts",
        path: "/dashboard/accounts",
        icon: <MdPeople />,
      },
      {
        title: "Withdrawal",
        path: "/dashboard/withdrawal",
        icon: <MdAttachMoney />,
      },
      {
        title: "Interest",
        path: "/dashboard/interest",
        icon: <MdPayment />,
      },
      {
        title: "Transfers",
        path: "/dashboard/transfer",
        icon: <MdSwapHoriz />,
      },
      {
        title: "External Transfers",
        path: "/dashboard/externalTransfer",
        icon: <MdSwapHoriz />,
      },
      {
        title: "Standing Balance",
        path: "/dashboard/balance",
        icon: <MdAccountBalanceWallet />,
      },
      {
        title: "Balance by date",
        path: "/dashboard/balanceByDate",
        icon: <MdAccountBalanceWallet />,
      },
      {
        title: "Account Statement",
        path: "/dashboard/accountStatement",
        icon: <MdAccountBalance />,
      },
    ],
  },
];

const Sidebar = () => {
  return (
    <div className={styles.container}>
      <ul className={styles.list}>
        {menuItems.map((cat) => (
          <li key={cat.title}>
            <span className={styles.cat}>{cat.title}</span>
            {cat.list.map((item) => (
              <MenuLink item={item} key={item.title} />
            ))}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
