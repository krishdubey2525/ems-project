import React from 'react';

const StatCardComponent = ({ title, value, icon, color }) => {
    return (
        <div className={`card shadow-sm border-0 border-start border-4 border-${color} h-100`}>
            <div className="card-body">
                <div className="row align-items-center">
                    <div className="col">
                        <div className={`text-xs font-weight-bold text-${color} text-uppercase mb-1`}>
                            {title}
                        </div>
                        <div className="h5 mb-0 font-weight-bold text-gray-800">{value}</div>
                    </div>
                    <div className="col-auto">
                        <i className={`fas ${icon} fa-2x text-gray-300`}></i>
                    </div>
                </div>
            </div>
        </div>
    );
};

// CRITICAL: This line tells React other files can use this component
export default StatCardComponent;