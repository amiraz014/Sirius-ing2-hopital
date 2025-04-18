import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const StockHistoryByMedicament = () => {
    const [medicaments, setMedicaments] = useState([]);

    useEffect(() => {
        const fetchHistoriqueParMedicament = async () => {
            try {
                const response = await axios.get('http://172.31.252.28:8089/stock/historique/par-medicament');
                setMedicaments(response.data); 
            } catch (error) {
                console.error('Erreur lors de la récupération de l\'historique par médicament', error);
            }
        };

        fetchHistoriqueParMedicament();
    }, []);

    return (
        <div className="p-8 bg-gray-900 text-white min-h-screen">
            <h2 className="text-2xl font-bold mb-6">Historique des Sorties par Médicament</h2>
            <div className="overflow-x-auto">
                <table className="w-full bg-gray-800 rounded-lg overflow-hidden">
                    <thead className="bg-gray-700">
                    <tr>
                        <th className="p-3 text-left">ID Médicament</th>
                        <th className="p-3 text-left">Nom</th>
                        <th className="p-3 text-left">Quantité Totale Sortie</th>
                        <th className="p-3 text-left">Dernière Sortie</th>
                        <th className="p-3 text-left">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {medicaments.map((medicament) => (
                        <tr key={medicament.idMedicament} className="border-b border-gray-700 hover:bg-gray-750 transition duration-200">
                            <td className="p-3">{medicament.idMedicament}</td>
                            <td className="p-3">{medicament.nomMedicament}</td>
                            <td className="p-3">{medicament.quantiteTotaleSortie}</td>
                            <td className="p-3">{new Date(medicament.derniereSortie).toLocaleDateString()}</td>
                            <td className="p-3">
                                <Link
                                    to={`/stock-history/medicament/${medicament.idMedicament}`}
                                    className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded transition duration-300"
                                >
                                    Voir détails
                                </Link>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default StockHistoryByMedicament;