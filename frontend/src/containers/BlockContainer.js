import React from "react";
import { AdditionalInfoComponent } from "./Info";
export const BlockContainer = ({ header,items,itemType,isLoading }) => {
  
  const renderItem = (item, i) => {
    switch (itemType) {
      case 'friends':
        return (
          <div className="card-container" key={i}>
            <div className="icon friends"></div>
            <div className="user">
              <div className="name">{item.fullname}</div>
            </div>
            <AdditionalInfoComponent
              time={item.time}
              unreads={item.unreads}
            />
          </div>
        );
      case 'groups':
        return (
          <div className="card-container" key={i}>
            <div className="icon groups"></div>
            <div className="group">
              <div className="name">{item.name}</div>
            </div>
            <AdditionalInfoComponent
              time={item.time}
              unreads={item.unreads}
            />
          </div>
        );
      case 'playlists':
        return (
          <div className="card-container" key={i}>
            <div className="icon"></div>
            <div className="playlist">
              <div className="name">{item.name}</div>
            </div>
          </div>
        );
      default:
        return null;
    }
  };

    return (
      <div className="block-container">
        <div className="header">{header}</div>
        <div className="content">
          {isLoading ? (  // Show loading spinner or message when data is still loading
          <div className="loading-message">Loading {itemType}...</div>
        ) : (           // Render items once loading is complete
          items.slice(0, 6).map((item, i) => (
            <React.Fragment key={i}>{renderItem(item, i)}</React.Fragment>
          ))
        )}
        </div>
      </div>
    );
};