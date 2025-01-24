import React, { useState } from "react";
import axios from "axios";

const StockSimulation = () => {
    const [simulationMessage, setSimulationMessage] = useState("");
    const [isSimulating, setIsSimulating] = useState(false);

    const handleStartSimulation = async () => {
        setIsSimulating(true);
        try {
            // Appel au backend pour démarrer la simulation de sorties
            const response = await axios.get("http://172.31.253.194:8089/stock/simulate");
            setSimulationMessage(response.data);
        } catch (error) {
            setSimulationMessage("Erreur lors de la simulation.");
        } finally {
            setIsSimulating(false);
        }
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
            <h1>Simulation de Sortie de Stock</h1>
            <button onClick={handleStartSimulation} disabled={isSimulating}>
                {isSimulating ? "Simulation en cours..." : "Démarrer la simulation"}
            </button>
            {simulationMessage && <p>{simulationMessage}</p>}
        </div>
    );
};

export default StockSimulation;
