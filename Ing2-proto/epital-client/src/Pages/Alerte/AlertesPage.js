import React, { useState, useEffect } from 'react';
import { FiAlertTriangle, FiCheckCircle, FiInfo } from 'react-icons/fi';
import AlerteCard from './AlerteCard';
import axios from 'axios';

const AlertesPage = () => {
    const [alertes, setAlertes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const fetchAlertes = async () => {
        try {
            const { data } = await axios.get('http://172.31.252.28:8089/stock/alertes');
            setAlertes(data);
        } catch (err) {
            setError('Erreur lors du chargement des alertes');
        } finally {
            setLoading(false);
        }
    };

    const confirmOrder = async (id) => {
        try {
            const { data } = await axios.post(`http://172.31.252.28:8089/stock/confirmer-commande/${id}`);
            setSuccess(data);
            fetchAlertes();
            setTimeout(() => setSuccess(null), 5000);
        } catch (err) {
            setError('Erreur lors de la confirmation');
        }
    };

    useEffect(() => { fetchAlertes(); }, []);

    if (loading) return (
        <div className="flex justify-center py-20">
            <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-blue-500"></div>
        </div>
    );

    return (
        <div className="p-4 md:p-6 max-w-7xl mx-auto">
            <h1 className="text-2xl font-bold mb-6 flex items-center gap-2">
                <FiAlertTriangle className="text-yellow-500" /> Alertes de Stock
            </h1>

           
            {error && (
                <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-4 rounded">
                    <div className="flex items-center gap-2">
                        <FiAlertTriangle /> {error}
                    </div>
                </div>
            )}

            {success && (
                <div className="bg-green-100 border-l-4 border-green-500 text-green-700 p-4 mb-4 rounded">
                    <div className="flex items-center gap-2">
                        <FiCheckCircle /> {success}
                    </div>
                </div>
            )}

           
            {alertes.length === 0 ? (
                <div className="bg-blue-100 border-l-4 border-blue-500 text-blue-700 p-4 rounded">
                    <div className="flex items-center gap-2">
                        <FiInfo /> Aucune alerte active
                    </div>
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                    {alertes.map((alerte, index) => (
                        <AlerteCard
                            key={index}
                            alerte={alerte}
                            onConfirmerCommande={confirmOrder}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};

export default AlertesPage;