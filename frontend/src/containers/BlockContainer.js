import React from "react";

export const BlockContainer = ({ header, items, renderItem }) => {
    return (
      <div className="block-container">
        <div className="header">{header}</div>
        <div className="content">
          {items.slice(0, 6).map((item, i) => (
            <React.Fragment key={i}>{renderItem(item, i)}</React.Fragment>
          ))}
        </div>
      </div>
    );
};