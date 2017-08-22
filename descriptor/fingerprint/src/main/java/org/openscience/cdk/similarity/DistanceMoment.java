package org.openscience.cdk.similarity;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

import javax.vecmath.Point3d;
import java.util.Iterator;

/**
 * Fast similarity measure for 3D structures.
 * 
 * This class implements a fast descriptor based 3D similarity measure described by Ballester et al
 * ({@cdk.cite BALL2007}). The approach calculates the distances of each atom to four specific points: the
 * centroid of the molecule, the atom that is closest to the centroid, the atom that is farthest from the
 * centroid and the atom that is farthest from the previous atom. Thus we get 4 sets of distance distributions.
 * The final descriptor set is generated by evaluating the first three moments of each distance distribution.
 * 
 * The similarity between two molecules is then evaluated using the inverse of a normalized
 * Manhattan type metric.
 * 
 * This class allows you to evaluate the 3D similarity between two specified molecules as well as
 * generate the 12 descriptors used to characterize the 3D structure which can then be used for a
 * variety of purposes such as storing in a database.
 *
 * <b>Note</b>: The methods of this class do not perform hydrogen removal. If you want to
 * do the calculations excluding hydrogens, you'll need to do it yourself. Also, if the molecule has
 * disconnected components, you should consider one (usually the largest), otherwise all components
 * are considered in the calculation.
 *
 * @author Rajarshi Guha
 * @cdk.created 2007-03-11
 * @cdk.keyword similarity, 3D, manhattan
 * @cdk.githash
 * @cdk.module fingerprint
 */
public class DistanceMoment {

    private static Point3d getGeometricCenter(IAtomContainer atomContainer) throws CDKException {
        double x = 0;
        double y = 0;
        double z = 0;

        for (IAtom atom : atomContainer.atoms()) {
            Point3d p = atom.getPoint3d();
            if (p == null) throw new CDKException("Molecule must have 3D coordinates");
            x += p.x;
            y += p.y;
            z += p.z;
        }
        x /= atomContainer.getAtomCount();
        y /= atomContainer.getAtomCount();
        z /= atomContainer.getAtomCount();
        return new Point3d(x, y, z);
    }

    private static float mu1(double[] x) {
        float sum = 0;
        for (double aX : x) {
            sum += aX;
        }
        return sum / x.length;
    }

    private static float mu2(double[] x, double mean) {
        float sum = 0;
        for (double aX : x) {
            sum += (aX - mean) * (aX - mean);
        }
        return sum / (x.length - 1);
    }

    private static float mu3(double[] x, double mean, double sigma) {
        float sum = 0;
        for (double aX : x) {
            sum += ((aX - mean) / sigma) * ((aX - mean) / sigma) * ((aX - mean) / sigma);
        }
        return sum / x.length;
    }

    /**
     * Evaluate the 12 descriptors used to characterize the 3D shape of a molecule.
     *
     * @param atomContainer The molecule to consider, should have 3D coordinates
     * @return A 12 element array containing the descriptors.
     * @throws CDKException if there are no 3D coordinates
     */
    public static float[] generateMoments(IAtomContainer atomContainer) throws CDKException {
        // lets check if we have 3D coordinates
        Iterator<IAtom> atoms;

        int natom = atomContainer.getAtomCount();

        Point3d ctd = getGeometricCenter(atomContainer);
        Point3d cst = new Point3d();
        Point3d fct = new Point3d();
        Point3d ftf = new Point3d();

        double[] distCtd = new double[natom];
        double[] distCst = new double[natom];
        double[] distFct = new double[natom];
        double[] distFtf = new double[natom];

        atoms = atomContainer.atoms().iterator();
        int counter = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        // eval dist to centroid
        while (atoms.hasNext()) {
            IAtom atom = atoms.next();
            Point3d p = atom.getPoint3d();
            double d = p.distance(ctd);
            distCtd[counter++] = d;

            if (d < min) {
                cst.x = p.x;
                cst.y = p.y;
                cst.z = p.z;
                min = d;
            }
            if (d > max) {
                fct.x = p.x;
                fct.y = p.y;
                fct.z = p.z;
                max = d;
            }
        }

        // eval dist to cst
        atoms = atomContainer.atoms().iterator();
        counter = 0;
        while (atoms.hasNext()) {
            IAtom atom = atoms.next();
            Point3d p = atom.getPoint3d();
            double d = p.distance(cst);
            distCst[counter++] = d;
        }

        // eval dist to fct
        atoms = atomContainer.atoms().iterator();
        counter = 0;
        max = Double.MIN_VALUE;
        while (atoms.hasNext()) {
            IAtom atom = atoms.next();
            Point3d p = atom.getPoint3d();
            double d = p.distance(fct);
            distFct[counter++] = d;

            if (d > max) {
                ftf.x = p.x;
                ftf.y = p.y;
                ftf.z = p.z;
                max = d;
            }
        }

        // eval dist to ftf
        atoms = atomContainer.atoms().iterator();
        counter = 0;
        while (atoms.hasNext()) {
            IAtom atom = atoms.next();
            Point3d p = atom.getPoint3d();
            double d = p.distance(ftf);
            distFtf[counter++] = d;
        }

        float[] moments = new float[12];

        float mean = mu1(distCtd);
        float sigma2 = mu2(distCtd, mean);
        float skewness = mu3(distCtd, mean, Math.sqrt(sigma2));
        moments[0] = mean;
        moments[1] = sigma2;
        moments[2] = skewness;

        mean = mu1(distCst);
        sigma2 = mu2(distCst, mean);
        skewness = mu3(distCst, mean, Math.sqrt(sigma2));
        moments[3] = mean;
        moments[4] = sigma2;
        moments[5] = skewness;

        mean = mu1(distFct);
        sigma2 = mu2(distFct, mean);
        skewness = mu3(distFct, mean, Math.sqrt(sigma2));
        moments[6] = mean;
        moments[7] = sigma2;
        moments[8] = skewness;

        mean = mu1(distFtf);
        sigma2 = mu2(distFtf, mean);
        skewness = mu3(distFtf, mean, Math.sqrt(sigma2));
        moments[9] = mean;
        moments[10] = sigma2;
        moments[11] = skewness;

        return moments;
    }

    /**
     * Evaluate the 3D similarity between two molecules.
     *
     * The method does not remove hydrogens. If this is required, remove them from the
     * molecules before passing them here.
     *
     * @param query  The query molecule
     * @param target The target molecule
     * @return The similarity between the two molecules (ranging from 0 to 1)
     * @throws CDKException if either molecule does not have 3D coordinates
     */
    public static float calculate(IAtomContainer query, IAtomContainer target) throws CDKException {
        float[] mom1 = generateMoments(query);
        float[] mom2 = generateMoments(target);
        float sum = 0;
        for (int i = 0; i < mom1.length; i++) {
            sum += Math.abs(mom1[i] - mom2[i]);
        }
        return (float) (1.0 / (1.0 + sum / 12.0));
    }
}
