import React, { useState, useEffect } from "react";
import axios from "axios";
 
const StockSimulation = () => {
  const [stockLogs, setStockLogs] = useState([]);
 
 
  const fetchSimulation = async () => {
    try {
      const response = await axios.get("http://172.31.253.194:8089/stock/simulate/once");
      setStockLogs((prevLogs) => [...prevLogs, response.data]); 
    } catch (error) {
      console.error("Erreur lors de la récupération des données", error);
    }
  };
 
  useEffect(() => {
    
    const interval = setInterval(() => {
      fetchSimulation();
    }, 5000);
 
    return () => clearInterval(interval);
  }, []);
 
  return (
<div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
<h1>Suivi des sorties de stock</h1>
<button onClick={fetchSimulation}>Lancer une sortie</button>
<ul>
        {stockLogs.map((log, index) => (
<li key={index}>{log}</li>
        ))}
</ul>
</div>
  );
};
 
export default StockSimulation;