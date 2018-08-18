function [nx,nP] = lidarMeasure(x,P,dt,m1,m2,m3,R,Q)
%measure lidar
%R: estimated lidar variance
%m = [posx,posy,theta]'
%m1-3: last 3 measurements

%compute variance and values for position and acceleration
p = m1;
R1 = R;
v = (m1-m2)/dt;
R2 = 2*R*(1/dt);
a = m1(1:2)-2*m2(1:2)+m3(1:2);
R3 = 4*R(1:2,1:2)*(1/dt^2);
fullz = [p;v;a];
fullR = blkdiag(R1,R2,R3);
Fx = getEvolution(x);
dotx = @(x)Fx*x;
if(dt > 0.00001)
    [px,pP]=Predict(x,P,dotx,Fx,dt,Q*dt);
else
    px = x;
    pP = P;
end
h = @(x)x;
Hx = eye(7);
[nx,nP]=kmeasure(px,pP,h,Hx,fullz,fullR);
end

