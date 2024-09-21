import React from "react";

// react bootstrap components
import ToastContainer from "react-bootstrap/ToastContainer";
import { Toast } from "react-bootstrap";

// third party
import { connect } from "react-redux";

// store
import { store } from "../../redux/store";

// actions
import { ActionTypes } from "../../actions/_types";
import { dispatchAction } from "../../utils";

const Notification = ({ notification }) => {
  const closeNotification = () => {
    dispatchAction(ActionTypes.HIDE_NOTIFICATION, {
      message: "",
      color: "secondary",
    });
  };

  return (
    <div className="notification-container">
      <ToastContainer position="bottom-center">
        <Toast
          onClose={closeNotification}
          show={notification.show}
          delay={notification.timeout}
          bg={notification.color}
          animation={false}
          autohide={true}
        >
          <Toast.Body>{notification.message}</Toast.Body>
        </Toast>
      </ToastContainer>
    </div>
  );
};
export default connect((store) => ({
  notification: store.notification,
}))(Notification);
