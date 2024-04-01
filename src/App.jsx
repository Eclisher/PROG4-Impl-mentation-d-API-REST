import React from "react";
import { Routes, Route } from "react-router-dom";
import MainLayout from "./layout/MainLayout";
import Blank from "./pages/Blank";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainLayout />}>
        <Route index element={<Blank />} />
        <Route path="removal" element={<MainLayout />} />
        <Route path="balance" element={<Blank />} />
        <Route path="provisioning" element={<Blank />} />
        <Route path="transfer" element={<Dashboard />} />
      </Route>
    </Routes>
  );
}

export default App;