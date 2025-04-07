import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import Epital from '../Epital';
import MainMenu from '../Home/Components/MainMenu';

const StockHistory = () => {
    const [historique, setHistorique] = useState([]);
    const con = useContext(Epital);

    useEffect(() => {
        const fetchHistorique = async () => {
            try {
                const response = await axios.get('http://172.31.252.28:8089/stock/historique');
                setHistorique(response.data);
            } catch (error) {
                console.error('Erreur lors de la récupération de l\'historique', error);
            }
        };

        fetchHistorique();
    }, []);

    return (
        <div className="flex flex-row justify-between bg-gray-900 text-white min-h-screen">
            <div>
                {con.showMenu && <MainMenu />}
            </div>
            <div class="flex flex-wrap flex-col gap-4 overflow-x-auto overflow-y-auto max-h-[80vh] p-2">
            <h2 className="text-2xl font-bold mb-6">Historique des Mouvements de Stock</h2>
            <div className="overflow-x-auto">
                <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
                    <thead className="bg-gray-700">
                    <tr>
                        <th className="p-3 text-left">ID Médicament</th>
                        <th className="p-3 text-left">Date</th>
                        <th className="p-3 text-left">Médicament</th>
                        <th className="p-3 text-left">Quantité Totale Sortie</th>
                        <th className="p-3 text-left">Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    {historique.map((mouvement) => (
                        <tr key={mouvement.idh} className="border-b border-gray-700 hover:bg-gray-750 transition duration-200">
                            <td className="p-3">{mouvement.medicament.idm}</td>
                            <td className="p-3">{new Date(mouvement.date_mouvement).toLocaleDateString()}</td>
                            <td className="p-3">{mouvement.medicament.nom}</td>
                            <td className="p-3">{mouvement.quantite}</td>
                            <td className="p-3">
                                    <span
                                        className={`px-2 py-1 rounded ${
                                            mouvement.type === 'SORTIE'
                                                ? 'bg-red-500 text-white'
                                                : 'bg-green-500 text-white'
                                        }`}
                                    >
                                        {mouvement.type}
                                    </span>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
            <div></div>
        </div>
    );
};

export default StockHistory;