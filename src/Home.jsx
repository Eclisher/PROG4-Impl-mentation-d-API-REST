// Home.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"; // Assurez-vous d'importer BrowserRouter en tant que Router
import App from "./App";
import ConnexionPage from "./components/ConnexionPage";

function Home() {
  return (
    <Router> {/* Utilisez BrowserRouter en tant que Router */}
      <Routes>
        <Route path="/homepage" element={<App />} />
        <Route path="/" element={<ConnexionPage />} />
      </Routes>
    </Router>
  );
}

export default Home;
