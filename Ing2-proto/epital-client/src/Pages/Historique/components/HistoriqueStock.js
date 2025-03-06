import React, { useState, useEffect } from 'react';
import axios from 'axios';

const HistoriqueStock = () => {
    const [historique, setHistorique] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchHistorique = async () => {
            try {
                const response = await axios.get("http://localhost:8089/stock/historique");
                setHistorique(response.data);
            } catch (error) {
                console.error("Erreur de chargement de l'historique", error);
            } finally {
                setLoading(false);
            }
        };

        fetchHistorique();
    }, []);

    return (
        <div className="flex flex-row justify-between bg-gray-900 min-h-screen text-white p-4">
            <div className="flex flex-col">
            <h2>📋 Historique des sorties de stock</h2>
            {loading ? <p>Chargement...</p> : (
                Object.keys(historique).length === 0 ? (
                    <p>Aucune sortie enregistrée.</p>
                ) : (
                    Object.entries(historique).map(([medicament, sorties]) => (
                        <div key={medicament} className="card mt-3">
                            <div className="card-header">
                                <h5 className="mb-0">{medicament}</h5>
                            </div>
                            <ul className="list-group list-group-flush">
                                {sorties.map((sortie, index) => (
                                    <li key={index} className="list-group-item">
                                        <strong>Date :</strong> {sortie.date_mouvement} <br />
                                        <strong>Quantité :</strong> {sortie.quantite} unités
                                    </li>
                                ))}
                            </ul>
                        </div>
                    ))
                )
            )}
        </div>
        </div>
    );
};

export default HistoriqueStock;
