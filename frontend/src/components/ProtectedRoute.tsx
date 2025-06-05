import React from 'react';
import { Navigate } from 'react-router-dom';

// Usage: <ProtectedRoute isAllowed={userIsLoggedIn}><Component /></ProtectedRoute>
const ProtectedRoute = ({ isAllowed, redirectPath = '/signin', children }) => {
  if (!isAllowed) {
    return <Navigate to={redirectPath} replace />;
  }
  return children;
};

export default ProtectedRoute;

