package net.posesor.subjects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface SubjectService {

    /**
     * Finds a SubjectDbModel with provided name.
     * If doesn't exist - it is automatically created.
     * @param subjectName name of the subject. If null or empty, some 'default name' will be used .
     * @return SubjectDbModel with provided name or with default name, if none provided.
     */
    @NotNull
    SubjectDbModel getOrCreateIdBySubjectName(@Nullable String subjectName);
}
