package com.scalar.cassy.traverser;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IncrementalBackupTraverserTest {
  private static final String DATA_DIR = "/tmp/" + UUID.randomUUID();
  private static final String KEYSPACE_DIR_1 = "keyspace1";
  private static final String KEYSPACE_DIR_2 = "keyspace2";
  private static final String TABLE_DIR_1 = "standard1-xxx";
  private static final String TABLE_DIR_2 = "standard2-xxx";
  private static final String BACKUP_DIR = IncrementalBackupTraverser.BACKUP_DIRNAME;
  private static final String FILE1 = "file1";
  private static final String FILE2 = "file2";
  private static final Joiner joiner = Joiner.on("/").skipNulls();
  private static final FileSystem fs = FileSystems.getDefault();
  private IncrementalBackupTraverser traverser;

  @BeforeClass
  public static void setUpBeforeClass() {
    getListOfFiles()
        .forEach(
            p -> {
              try {
                Files.createDirectories(p.getParent());
                Files.createFile(p);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            });
  }

  @AfterClass
  public static void tearDownAfterClass() throws IOException {
    Files.walk(Paths.get(DATA_DIR))
        .map(Path::toFile)
        .sorted(Comparator.reverseOrder())
        .forEach(File::delete);
  }

  private static List<Path> getListOfFiles() {
    return Arrays.asList(
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_1, BACKUP_DIR, FILE1)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_1, BACKUP_DIR, FILE2)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_2, BACKUP_DIR, FILE1)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_2, BACKUP_DIR, FILE2)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_2, TABLE_DIR_1, BACKUP_DIR, FILE1)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_2, TABLE_DIR_1, BACKUP_DIR, FILE2)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_2, TABLE_DIR_2, BACKUP_DIR, FILE1)),
        fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_2, TABLE_DIR_2, BACKUP_DIR, FILE2)));
  }

  @Before
  public void setUp() {
    this.traverser = new IncrementalBackupTraverser(FileSystems.getDefault().getPath(DATA_DIR));
  }

  @Test
  public void traverse_KeyspaceGiven_ReturnPathsWithTheKeyspace() {
    // Arrange

    // Act
    List<Path> files = traverser.traverse(KEYSPACE_DIR_1);

    // Assert
    assertThat(files)
        .containsOnly(
            fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_1, BACKUP_DIR, FILE1)),
            fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_1, BACKUP_DIR, FILE2)),
            fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_2, BACKUP_DIR, FILE1)),
            fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_2, BACKUP_DIR, FILE2)));
  }

  @Test
  public void traverse_KeyspaceAndTableGiven_ReturnPathsWithTheKeyspaceAndTheTable() {
    // Arrange

    // Act
    List<Path> files = traverser.traverse(KEYSPACE_DIR_1, TABLE_DIR_2);

    // Assert
    assertThat(files)
        .containsOnly(
            fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_2, BACKUP_DIR, FILE1)),
            fs.getPath(joiner.join(DATA_DIR, KEYSPACE_DIR_1, TABLE_DIR_2, BACKUP_DIR, FILE2)));
  }
}
