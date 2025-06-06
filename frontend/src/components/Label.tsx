import React from "react";

interface LabelProps {
  htmlFor?: string;
  children: React.ReactNode;
  className?: string;
}

const Label: React.FC<LabelProps> = ({ htmlFor, children, className = "" }) => (
  <label
    htmlFor={htmlFor}
    className={`text-sm font-medium text-gray-700 mb-1 w-[120px] whitespace-nowrap  overflow-hidden text-ellipsis inline-block align-middle ${className}`}
  >
    {children}
  </label>
);

export default Label;
