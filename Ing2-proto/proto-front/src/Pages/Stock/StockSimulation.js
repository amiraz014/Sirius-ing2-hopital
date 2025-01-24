import React, { useState, useEffect } from "react";
import axios from "axios";

const StockSimulation = () => {
    const [stockLogs, setStockLogs] = useState([]);
    const [isSimulating, setIsSimulating] = useState(false);

    const startSimulation = async () => {
        setIsSimulating(true);
        try {
            const response = await axios.get("http://172.31.253.194:8089/stock/simulate");
            if (response.status === 200) {
                setStockLogs((prevLogs) => [...prevLogs, response.data]);
            }
        } catch (error) {
            console.error("Erreur lors de la simulation", error);
        } finally {
            setIsSimulating(false);
        }
    };

    useEffect(() => {
        const interval = setInterval(() => {
            startSimulation();
        }, 5000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div>
            <h1>Suivi des sorties de stock</h1>
            <button onClick={startSimulation} disabled={isSimulating}>
                {isSimulating ? "Simulation en cours..." : "Démarrer la simulation"}
            </button>
            <ul>
                {stockLogs.map((log, index) => (
                    <li key={index}>{log}</li>
                ))}
            </ul>
        </div>
    );
};

export default StockSimulation;
