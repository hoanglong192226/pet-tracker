const MemberLayout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="flex flex-col">
      <div>Title</div>
      {children}
    </div>
  );
};

export default MemberLayout;
