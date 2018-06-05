/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon;

import org.apache.log4j.Level;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Exclusion;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Create a Spoon launcher from a maven pom file
 */
public class MavenLauncher extends Launcher {
	private String m2RepositoryPath;
	private SOURCE_TYPE sourceType;

	/**
	 * The type of source to consider in the model
	 */
	public enum SOURCE_TYPE {
		// only the main code of the application
		APP_SOURCE,
		// only the tests of the application
		TEST_SOURCE,
		// all the sources
		ALL_SOURCE
	}

	public MavenLauncher(String mavenProject, SOURCE_TYPE sourceType) {
		this(mavenProject, Paths.get(System.getProperty("user.home"), ".m2", "repository").toString(), sourceType);
	}

	/**
	 *
	 * @param mavenProject the path to the root of the project
	 * @param m2RepositoryPath the path to the m2repository
	 */
	public MavenLauncher(String mavenProject, String m2RepositoryPath, SOURCE_TYPE sourceType) {
		super();
		this.m2RepositoryPath = m2RepositoryPath;
		this.sourceType = sourceType;

		File mavenProjectFile = new File(mavenProject);
		if (!mavenProjectFile.exists()) {
			throw new SpoonException(mavenProject + " does not exist.");
		}

		InheritanceModel model;
		try {
			model = readPOM(mavenProject, null);
		} catch (Exception e) {
			throw new SpoonException("Unable to read the pom", e);
		}
		if (model == null) {
			throw new SpoonException("Unable to create the model, pom not found?");
		}

		// app source
		if (SOURCE_TYPE.APP_SOURCE == sourceType || SOURCE_TYPE.ALL_SOURCE == sourceType) {
			List<File> sourceDirectories = model.getSourceDirectories();
			for (File sourceDirectory : sourceDirectories) {
				this.addInputResource(sourceDirectory.getAbsolutePath());
			}
		}

		// test source
		if (SOURCE_TYPE.TEST_SOURCE == sourceType || SOURCE_TYPE.ALL_SOURCE == sourceType) {
			List<File> testSourceDirectories = model.getTestDirectories();
			for (File sourceDirectory : testSourceDirectories) {
				this.addInputResource(sourceDirectory.getAbsolutePath());
			}
		}

		// dependencies
		ProjectDependence depTree = model.getDependencies();
		List<File> dependencies = depTree.toFiles();
		String[] classpath = new String[dependencies.size()];
		for (int i = 0; i < dependencies.size(); i++) {
			File file = dependencies.get(i);
			classpath[i] = file.getAbsolutePath();
		}
		this.getModelBuilder().setSourceClasspath(classpath);

		// compliance level
		this.getEnvironment().setComplianceLevel(model.getSourceVersion());
	}

	/**
	 * Extract the information from the pom
	 * @param path the path to the pom
	 * @param parent the parent pom
	 * @return the extracted model
	 * @throws IOException when the file does not exist
	 * @throws XmlPullParserException when the file is corrupted
	 */
	private InheritanceModel readPOM(String path, InheritanceModel parent) throws IOException, XmlPullParserException {
		if (!path.endsWith(".xml") && !path.endsWith(".pom")) {
			path = Paths.get(path, "pom.xml").toString();
		}
		File pomFile = new File(path);
		if (!pomFile.exists()) {
			return null;
		}
		MavenXpp3Reader pomReader = new MavenXpp3Reader();
		try (FileReader reader = new FileReader(pomFile)) {
			Model model = pomReader.read(reader);
			InheritanceModel inheritanceModel = new InheritanceModel(model, parent, pomFile.getParentFile());
			for (String module : model.getModules()) {
				if (path.contains(m2RepositoryPath)) {
					InheritanceModel modulePom = readPOM(path.replaceAll(model.getArtifactId(), module), inheritanceModel);
					if (modulePom != null) {
						inheritanceModel.addModule(modulePom);
					}
				} else {
					inheritanceModel.addModule(readPOM(Paths.get(pomFile.getParent(), module).toString(), inheritanceModel));
				}
			}
			return inheritanceModel;
		}
	}

	class ProjectDependence {
		private String groupId;
		private String artifactId;
		private String version;
		private List<ProjectDependence> dependencies = new ArrayList<>();

		ProjectDependence(String groupId, String artifactId, String version) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

		public void addDependence(ProjectDependence dependence) {
			if (dependence != null) {
				dependencies.add(dependence);
			}
		}

		public List<ProjectDependence> getAllProjectDependences() {
			List<ProjectDependence> output = new ArrayList<>();
			for (ProjectDependence projectDependence : dependencies) {
				output.add(projectDependence);
			}
			for (ProjectDependence projectDependence : dependencies) {
				output.addAll(projectDependence.getAllProjectDependences());
			}
			return output;
		}


		public List<File> toFiles() {
			List<ProjectDependence> deps = getAllProjectDependences();
			List<File> output = new ArrayList<>();
			Set<ProjectDependence> addedDep = new HashSet<>();
			for (int i = 0; i < deps.size(); i++) {
				ProjectDependence dep = deps.get(i);
				File file = dep.toFile();
				if (null != file && !addedDep.contains(dep)) {
					addedDep.add(dep);
					output.add(file);
				}
			}
			return output;
		}

		private File toFile() {
			if (groupId != null && version != null) {
				String fileName = artifactId + "-" + version;
				Path depPath = Paths.get(m2RepositoryPath, groupId.replaceAll("\\.", "/"), artifactId, version);
				File depFile = depPath.toFile();
				if (depFile.exists()) {
					File jarFile = Paths.get(depPath.toString(), fileName + ".jar").toFile();
					if (jarFile.exists()) {
						return jarFile;
					}
				}
			}
			return null;
		}

		public void removeDependence(String groupId, String artifactId) {
			for (ProjectDependence dep : new ArrayList<>(dependencies)) {
				if (dep.groupId != null && dep.groupId.equals(groupId) && dep.artifactId != null && dep.artifactId.equals(artifactId)) {
					this.dependencies.remove(dep);
				} else {
					dep.removeDependence(groupId, artifactId);
				}
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			ProjectDependence that = (ProjectDependence) o;
			return Objects.equals(groupId, that.groupId)
					&& Objects.equals(artifactId, that.artifactId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(groupId, artifactId);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(groupId);
			sb.append(":");
			sb.append(artifactId);
			sb.append(":");
			sb.append(version);
			if (!dependencies.isEmpty()) {
				sb.append(" {\n");
				for (int i = 0; i < dependencies.size(); i++) {
					ProjectDependence dep = dependencies.get(i);
					String child = dep.toString();
					for (String s : child.split("\n")) {
						sb.append("\t");
						sb.append(s);
						sb.append("\n");
					}
				}
				sb.append("}");
			}
			return sb.toString();
		}
	}

	class InheritanceModel {
		private List<InheritanceModel> modules = new ArrayList<>();
		private Model model;
		private InheritanceModel parent;
		private File directory;
		private Map<String, String> dependencyManagements = new HashMap<>();

		InheritanceModel(Model model, InheritanceModel parent, File directory) {
			this.model = model;
			this.parent = parent;
			this.directory = directory;
			// if possible, build the parent model from the relative path
			if (parent == null && model.getParent() != null) {
				try {
					File parentPath = new File(directory, model.getParent().getRelativePath());
					this.parent = readPOM(parentPath.getPath(), null);
					if (this.parent == null) {
						String groupId = model.getParent().getGroupId();
						String version = model.getParent().getVersion();
						this.parent = readPom(groupId, model.getParent().getArtifactId(), version);
					}
				} catch (Exception e) {
					LOGGER.debug("Parent model cannot be resolved: " + e.getMessage());
				}
			}
			DependencyManagement dependencyManagement = model.getDependencyManagement();
			if (dependencyManagement != null) {
				List<Dependency> dependencies = dependencyManagement.getDependencies();
				for (Dependency dependency : dependencies) {
					if ("import".equals(dependency.getScope())) {
						InheritanceModel pom = readPom(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
						if (pom != null) {
							for (String depKey : pom.dependencyManagements.keySet()) {
								if (!dependencyManagements.containsKey(depKey)) {
									dependencyManagements.put(depKey, pom.dependencyManagements.get(depKey));
								}
							}
						}
					} else {
						String depKey = dependency.getGroupId() + ":" + dependency.getArtifactId();
						if (!dependencyManagements.containsKey(depKey)) {
							dependencyManagements.put(depKey, extractVersion(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion()));
						}
					}
				}
			}
		}

		public void addModule(InheritanceModel module) {
			modules.add(module);
		}

		public Model getModel() {
			return model;
		}

		/**
		 * Get the parent model
		 * @return the parent model
		 */
		public InheritanceModel getParent() {
			return parent;
		}

		/**
		 * Get the list of source directories of the project
		 * @return the list of source directories
		 */
		public List<File> getSourceDirectories() {
			List<File> output = new ArrayList<>();
			String sourcePath = null;

			Build build = model.getBuild();
			if (build != null) {
				sourcePath = build.getSourceDirectory();
			}
			if (sourcePath == null) {
				sourcePath = Paths.get(directory.getAbsolutePath(), "src", "main", "java").toString();
			}
			File source = new File(sourcePath);
			if (source.exists()) {
				output.add(source);
			}
			File generatedSource = Paths.get(directory.getAbsolutePath(), "target", "generated-sources").toFile();
			if (generatedSource.exists()) {
				output.add(generatedSource);
			}
			for (InheritanceModel module : modules) {
				output.addAll(module.getSourceDirectories());
			}
			return output;
		}

		/**
		 * Get the list of test directories of the project
		 * @return the list of test directories
		 */
		public List<File> getTestDirectories() {
			List<File> output = new ArrayList<>();
			String sourcePath = null;

			Build build = model.getBuild();
			if (build != null) {
				sourcePath = build.getTestSourceDirectory();
			}
			if (sourcePath == null) {
				sourcePath = Paths.get(directory.getAbsolutePath(), "src", "test", "java").toString();
			}
			File source = new File(sourcePath);
			if (source.exists()) {
				output.add(source);
			}
			File generatedSource = Paths.get(directory.getAbsolutePath(), "target", "generated-test-sources").toFile();
			if (generatedSource.exists()) {
				output.add(generatedSource);
			}
			for (InheritanceModel module : modules) {
				output.addAll(module.getTestDirectories());
			}
			return output;
		}

		/**
		 * Extract the variable from a string
		 */
		private String extractVariable(String value) {
			if (value != null && value.startsWith("$")) {
				value = getProperty(value.substring(2, value.length() - 1));
			}
			return value;
		}

		private String extractVersion(String groupId, String artifactId, String version) {
			if (version == null) {
				String depKey = groupId + ":" + artifactId;
				if (dependencyManagements.containsKey(depKey)) {
					return dependencyManagements.get(depKey);
				} else if (this.parent != null) {
					return this.parent.extractVersion(groupId, artifactId, version);
				}
			}
			version = extractVariable(version);
			// TODO: Handle range version
			if (version != null && version.startsWith("[")) {
				version = version.substring(1, version.indexOf(','));
			}
			return version;
		}

		private InheritanceModel readPom(String groupId, String artifactId, String version) {
			version = extractVersion(groupId, artifactId, version);
			groupId = groupId.replace(".", "/");
			String fileName = artifactId + "-" + version;
			Path depPath = Paths.get(m2RepositoryPath, groupId, artifactId, version, fileName + ".pom");
			try {
				return readPOM(depPath.toString(), null);
			} catch (Exception e) {
				return null;
			}
		}

		private ProjectDependence getDependencies(Dependency dependency, boolean isLib, Set<ProjectDependence> hierarchy) {
			String version = extractVersion(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
			if (version == null) {
				LOGGER.warn("A dependency version cannot be resolved: " + dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + version);
				return null;
			}
			// pass only the optional dependency if it's in a library dependency
			if (isLib && dependency.isOptional()) {
				return null;
			}

			// ignore test dependencies for app source code
			if ("test".equals(dependency.getScope()) && (SOURCE_TYPE.APP_SOURCE == sourceType || isLib)) {
				return null;
			}
			// ignore not transitive dependencies
			if (isLib && ("test".equals(dependency.getScope()) || "provided".equals(dependency.getScope()) || "compile".equals(dependency.getScope()))) {
				LOGGER.log(Level.WARN, "Dependency ignored (scope: provided or test):" + dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + version);
				return null;
			}
			ProjectDependence dependence = new ProjectDependence(dependency.getGroupId(), dependency.getArtifactId(), version);
			if (hierarchy.contains(dependence)) {
				//return null;
			}
			try {
				InheritanceModel dependencyModel = readPom(dependency.getGroupId(), dependency.getArtifactId(), version);
				if (dependencyModel != null) {
					dependence = dependencyModel.getDependencies(true, hierarchy);
					dependence.groupId = dependency.getGroupId();
					dependence.artifactId = dependency.getArtifactId();
					dependence.version = version;

					if (dependency.getExclusions() != null) {
						for (int i = 0; i < dependency.getExclusions().size(); i++) {
							Exclusion exclusion = dependency.getExclusions().get(i);
							dependence.removeDependence(exclusion.getGroupId(), exclusion.getArtifactId());
						}
					}
				}

			} catch (Exception ignore) {
				// ignore the dependencies of the dependency
				ignore.printStackTrace();
			}
			return dependence;
		}

		private ProjectDependence getDependencies() {
			return getDependencies(false, new HashSet<>());
		}

		/**
		 * Get the list of dependencies available in the local maven repository
		 *
		 * @param isLib: If false take dependency of the main project; if true, take dependencies of a library of the project
		 * @return the list of  dependencies
		 */
		private ProjectDependence getDependencies(boolean isLib, Set<ProjectDependence> hierarchy) {
			ProjectDependence dependence = new ProjectDependence(model.getGroupId(), model.getArtifactId(), model.getVersion());
			if (hierarchy.contains(dependence)) {
				return dependence;
			}
			hierarchy.add(dependence);


			// add the parent has a dependency
			if (this.parent != null) {
				dependence.addDependence(this.parent.getDependencies(isLib, hierarchy));
			}

			List<Dependency> dependencies = model.getDependencies();
			for (Dependency dependency : dependencies) {
				dependence.addDependence(getDependencies(dependency, isLib, hierarchy));
			}

			if (!isLib) {
				for (InheritanceModel module : modules) {
					if (module.model.getGroupId() == null) {
						module.model.setGroupId(model.getGroupId());
					}
					if (module.model.getVersion() == null) {
						module.model.setVersion(model.getVersion());
					}
					dependence.addDependence(module.getDependencies(isLib, hierarchy));
				}
			}
			return dependence;
		}

		/**
		 * Get the value of a property
		 * @param key the key of the property
		 * @return the property value if key exists or null
		 */
		private String getProperty(String key) {
			if ("project.version".equals(key)) {
				if (model.getVersion() != null) {
					return model.getVersion();
				}
			}
			String value = extractVariable(model.getProperties().getProperty(key));
			if (value == null) {
				if (parent == null) {
					return null;
				}
				return parent.getProperty(key);
			}
			return value;
		}

		/**
		 * Get the source version of the project
		 * @return the source version of the project
		 */
		public int getSourceVersion() {
			if (model.getBuild() != null) {
				for (Plugin plugin : model.getBuild().getPlugins()) {
					if (!"maven-compiler-plugin".equals(plugin.getArtifactId())) {
						continue;
					}
					Xpp3Dom configuration = (Xpp3Dom) plugin.getConfiguration();
					Xpp3Dom source = configuration.getChild("source");
					if (source != null) {
						return Integer.parseInt(extractVariable(source.getValue()).substring(2));
					}
					break;
				}
			}
			String javaVersion = getProperty("java.version");
			if (javaVersion != null) {
				return Integer.parseInt(extractVariable(javaVersion).substring(2));
			}
			javaVersion = getProperty("java.src.version");
			if (javaVersion != null) {
				return Integer.parseInt(extractVariable(javaVersion).substring(2));
			}
			javaVersion = getProperty("maven.compiler.source");
			if (javaVersion != null) {
				return Integer.parseInt(extractVariable(javaVersion).substring(2));
			}
			javaVersion = getProperty("maven.compile.source");
			if (javaVersion != null) {
				return Integer.parseInt(extractVariable(javaVersion).substring(2));
			}
			// return the current compliance level of spoon
			return getEnvironment().getComplianceLevel();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(model.getGroupId() + ":" + model.getArtifactId() + ":" + model.getVersion());
			if (modules.isEmpty()) {
				return sb.toString();
			}
			sb.append(" {\n");
			for (int i = 0; i < modules.size(); i++) {
				InheritanceModel inheritanceModel =  modules.get(i);
				String child = inheritanceModel.toString();
				for (String s : child.split("\n")) {
					sb.append("\t");
					sb.append(s);
					sb.append("\n");
				}
			}
			sb.append("}");
			return sb.toString();
		}
	}
}
