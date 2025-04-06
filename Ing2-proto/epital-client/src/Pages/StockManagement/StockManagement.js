import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import Epital from '../Epital';
import MainMenu from '../Home/Components/MainMenu';


const StockManagement = () => {
    const [sorties, setSorties] = useState([]);
    const [isRunning, setIsRunning] = useState(false); 
    const [error, setError] = useState(null); 
    const con = useContext(Epital);


    const fetchSorties = async () => {
        try {
            const response = await axios.get('http://172.31.252.28:8089/stock/sorties');
            setSorties(response.data); 
            setError(null);
        } catch (error) {
            console.error('Erreur lors de la récupération des sorties', error);
            setError('Erreur lors de la récupération des sorties');
        }
    };

    const handleStartSorties = async () => {
        try {
            await axios.get('http://172.31.252.28:8089/stock/lancer-sorties');
            setIsRunning(true);
            setError(null);
        } catch (error) {
            console.error('Erreur lors du démarrage des sorties automatiques', error);
            setError('Erreur lors du démarrage des sorties automatiques');
        }
    };


    const handleStopSorties = () => {
        setIsRunning(false);
    };


    useEffect(() => {
        let interval;
        if (isRunning) {
            interval = setInterval(fetchSorties, 2000);
        }
        return () => clearInterval(interval);
    }, [isRunning]);

    return (
        <div className="flex flex-row justify-between bg-gray-900 text-white min-h-screen">
            <div>{con.showMenu && <MainMenu/>}</div>
            <div>
            <h2 className="text-2xl font-bold mb-6">Sortie Automatique de Stock</h2>
            <div className="space-y-4 max-w-md mx-auto">
                {/* Boutons pour démarrer et arreter les sorties */}
                <div className="flex space-x-4">
                    <button
                        onClick={handleStartSorties}
                        className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded transition duration-300"
                        disabled={isRunning}
                    >
                        Démarrer les Sorties Automatiques
                    </button>
                    <button
                        onClick={handleStopSorties}
                        className="w-full bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded transition duration-300"
                        disabled={!isRunning}
                    >
                        Arrêter les Sorties Automatiques
                    </button>
                </div>

                {/* Afficher les erreurs */}
                {error && (
                    <p className="mt-4 text-center text-red-500">{error}</p>
                )}

                {/* Afficher les sorties */}
                <div className="mt-6">
                    <h3 className="text-xl font-semibold mb-4">Sorties effectuées :</h3>
                    <ul className="space-y-2">
                        {sorties.map((sortie, index) => (
                            <li
                                key={index}
                                className={`p-3 rounded-lg ${
                                    sortie.startsWith("🔴") ? "bg-red-900" :
                                        sortie.startsWith("⚠️") ? "bg-yellow-800" :
                                            "bg-gray-800"
                                }`}
                            >
                                {sortie}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
            </div>
            <div></div>
        </div>
    );
};

export default StockManagement;