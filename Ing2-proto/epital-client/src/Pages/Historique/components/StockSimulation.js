import React, { useState } from 'react';
import axios from 'axios';

const StockSimulation = () => {
    const [message, setMessage] = useState("");

    const handleSimulation = async () => {
        try {
            const response = await axios.get("http://localhost:8089/stock/simulate/once");
            setMessage(response.data);
        } catch (error) {
            setMessage("❌ Erreur lors de la simulation.");
        }
    };

    return (
        <div className="container text-center mt-5">
            <h2>Simulation de sortie de stock</h2>
            <button className="btn btn-primary" onClick={handleSimulation}>
                Gérer une sortie
            </button>
            {message && <p className="mt-3 alert alert-info">{message}</p>}
        </div>
    );
};

export default StockSimulation;
