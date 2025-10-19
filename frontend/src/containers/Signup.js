import React, { useState, useCallback } from "react";

// components
import InputField from "../components/_common/InputField";
import Button from "../components/_common/Button";
import Logo from "../components/_common/Logo";

// third party
import { connect } from "react-redux";
import { Link } from "react-router-dom";

import { useNavigate } from "react-router-dom"; // Import useNavigate

// actions
import { ActionTypes } from "../actions/_types";
import { handleSignup } from "../actions/signup";
import { dispatchAction } from "../utils";

const Signup = ({ signup }) => {
  const [data, setData] = useState({
    fullName: "",
    userName: "",
    email: "",
    password: "",
  });
  const { loading, error = {} } = signup;

  const handleForm = (field, value) => {
    setData({
      ...data,
      [field]: value,
    });
    if (error.fields) {
      dispatchAction(ActionTypes.UPDATE_SIGNUP_STATE, {
        error: {},
      });
    }
  };

  const navigate = useNavigate(); // Get navigate function
  const handleSubmit = useCallback(async () => {
    await handleSignup(data, navigate);
  }, [data, navigate]);

  const validations = error.fields || {};

  return (
    <div className="section-container signup-container">
      <div className="signup-block">
        <div className="form-container">
          <div className="header">Sign Up</div>
          <InputField
            label="Full Name"
            value={data.fullName}
            onChange={(e) => handleForm("fullName", e.target.value)}
            validationMessage={validations.fullName || ""}
          />
          <InputField
            label="Username"
            value={data.userName}
            onChange={(e) => handleForm("userName", e.target.value)}
            validationMessage={validations.userName || ""}
          />
          <InputField
            label="Email"
            value={data.email}
            onChange={(e) => handleForm("email", e.target.value)}
            validationMessage={validations.email || ""}
          />
          <InputField
            label="Password"
            type="password"
            value={data.password}
            onChange={(e) => handleForm("password", e.target.value)}
            validationMessage={validations.password || ""}
          />
          <Button handleOnClick={handleSubmit} loading={loading}>
            Sign Up
          </Button>
          <div className="sign-up">
            Already have an account? <Link to="/login">Login</Link>
          </div>
        </div>
        <div className="stock-img">
          <Logo showText={false} inverted={true} />
          <img src="/stock/signup2.png" alt="" />
        </div>
      </div>
    </div>
  );
};
export default connect((store) => ({
  signup: store.signup,
}))(Signup);
