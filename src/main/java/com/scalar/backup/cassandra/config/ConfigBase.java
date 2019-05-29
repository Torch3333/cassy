package com.scalar.backup.cassandra.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ConfigBase {
  private final Properties props;
  private static final String PREFIX = "scalar.backup.cassandra.";
  public static final String CLUSTER_ID = PREFIX + "cluster_id";
  public static final String BACKUP_ID = PREFIX + "backup_id";
  public static final String BACKUP_TYPE = PREFIX + "backup_type";
  public static final String TARGET_IP = PREFIX + "target_ip";
  public static final String DATA_DIR = PREFIX + "data_dir";
  public static final String DEST_BASE_URI = PREFIX + "dest_base_uri";
  public static final String KEYSPACE = PREFIX + "keyspace";
  private String clusterId;
  private String backupId;
  private BackupType backupType;
  private String targetIp;
  private String dataDir;
  private String destBaseUri;
  private String keyspace;

  public ConfigBase(File propertiesFile) throws IOException {
    this(new FileInputStream(propertiesFile));
  }

  public ConfigBase(InputStream stream) throws IOException {
    props = new Properties();
    props.load(stream);
    load();
  }

  public ConfigBase(Properties properties) {
    props = new Properties(properties);
    load();
  }

  public Properties getProperties() {
    return props;
  }

  public String getClusterId() {
    return clusterId;
  }

  public String getBackupId() {
    return backupId;
  }

  public BackupType getBackupType() {
    return backupType;
  }

  public String getTargetIp() {
    return targetIp;
  }

  public String getDataDir() {
    return dataDir;
  }

  public String getDestBaseUri() {
    return destBaseUri;
  }

  public String getKeyspace() {
    return keyspace;
  }

  private void load() {
    checkArgument(props.getProperty(CLUSTER_ID) != null);
    clusterId = checkNotNull(props.getProperty(CLUSTER_ID));
    checkArgument(props.getProperty(BACKUP_ID) != null);
    backupId = checkNotNull(props.getProperty(BACKUP_ID));
    checkArgument(props.getProperty(BACKUP_TYPE) != null);
    backupType =
        checkNotNull(BackupType.getByType(Integer.parseInt(props.getProperty(BACKUP_TYPE))));
    checkArgument(props.getProperty(TARGET_IP) != null);
    targetIp = checkNotNull(props.getProperty(TARGET_IP));
    checkArgument(props.getProperty(DATA_DIR) != null);
    dataDir = checkNotNull(props.getProperty(DATA_DIR));
    checkArgument(props.getProperty(DEST_BASE_URI) != null);
    destBaseUri = checkNotNull(props.getProperty(DEST_BASE_URI));
    checkArgument(props.getProperty(KEYSPACE) != null);
    keyspace = checkNotNull(props.getProperty(KEYSPACE));
  }
}
