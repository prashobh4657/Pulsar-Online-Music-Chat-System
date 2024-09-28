export const AdditionalInfoComponent = ({ time, unreads }) => (
    <div className="info">
      <div className="date-time">{time}</div>
      {unreads > 0 && <div className="unreads">{unreads}</div>}
    </div>
  );
  