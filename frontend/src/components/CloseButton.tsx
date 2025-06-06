import React from "react";

interface CloseButtonProps {
    onClick: () => void;
    className?: string;
    ariaLabel?: string;
}

const CloseButton: React.FC<CloseButtonProps> = ({ onClick, className = "", ariaLabel = "Close" }) => (
    <button
        className={`absolute top-2 right-2 text-gray-500 hover:text-gray-700 text-xl font-bold focus:outline-none ${className}`}
        onClick={onClick}
        aria-label={ariaLabel}
        type="button"
    >
        &times;
    </button>
);

export default CloseButton;

