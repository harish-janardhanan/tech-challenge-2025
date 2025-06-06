import React from "react";
import Label from "./Label";
import Input from "./Input";

interface InputRowProps {
  label: string;
  htmlFor?: string;
  inputProps?: React.ComponentProps<typeof Input>;
  labelClassName?: string;
  inputClassName?: string;
}

const InputRow: React.FC<InputRowProps> = ({
  label,
  htmlFor,
  inputProps = {},
  labelClassName = "",
  inputClassName = "",
}) => (
  <div className="mb-4 flex flex-row">
      <Label htmlFor={htmlFor} className={labelClassName}>{label}</Label>
    <Input id={htmlFor} className={inputClassName} {...inputProps} />
  </div>
);

export default InputRow;
