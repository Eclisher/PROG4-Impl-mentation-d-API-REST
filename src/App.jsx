// Home.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ConnexionPage from "./components/ConnexionPage";

function App() {
  return (
    <Router> {}
      <Routes>
        <Route path="/dashboard" />
        <Route path="/" element={<ConnexionPage />} />
      </Routes>
    </Router>
  );
}

export default App;
