import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import Epital from "../Epital";
import MainMenu from "../Home/Components/mainMenu";
 
const StockSimulation = () => {
  const [stockLogs, setStockLogs] = useState([]);
 
 
  const fetchSimulation = async () => {
    try {
      const response = await axios.get("http://172.31.249.204:8089/stock/simulate/once");
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
    const con = useContext(Epital);
  return (
    <>
    <div class="flex flex-row justify-between bg-gray-900">
      <div>{con.showMenu && <MainMenu/>}</div>
    <div className="flex flex-col items-center justify-center min-h-screen  text-white">
<h1>Suivi des sorties de stock</h1>
<button onClick={fetchSimulation}>Lancer une sortie</button>
<ul>
        {stockLogs.map((log, index) => (
<li key={index}>{log}</li>
        ))}
</ul>
</div>
<div></div>
</div>
</>

  );
};
 
export default StockSimulation;